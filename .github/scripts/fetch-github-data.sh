#!/bin/bash
set -euo pipefail

ORG="bright-room"
FILES_DIR="src/commonMain/composeResources/files"

# Requires: jq, curl
# In GitHub Actions, GITHUB_TOKEN is automatically available

AUTH_HEADER=""
if [ -n "${GITHUB_TOKEN:-}" ]; then
    AUTH_HEADER="Authorization: Bearer ${GITHUB_TOKEN}"
fi

api_get() {
    local url="$1"
    if [ -n "$AUTH_HEADER" ]; then
        curl -s -H "$AUTH_HEADER" -H "Accept: application/vnd.github+json" "$url"
    else
        curl -s -H "Accept: application/vnd.github+json" "$url"
    fi
}

echo "=== Fetching GitHub data for ${ORG} ==="

# --- Stats ---
echo "Fetching organization stats..."

# Get all repos
repos_json=$(api_get "https://api.github.com/orgs/${ORG}/repos?per_page=100&type=public")
repo_count=$(echo "$repos_json" | jq 'length')
total_stars=$(echo "$repos_json" | jq '[.[].stargazers_count] | add // 0')

# Get contributors count (unique across all repos)
contributor_set=""
for repo_name in $(echo "$repos_json" | jq -r '.[].name'); do
    contributors=$(api_get "https://api.github.com/repos/${ORG}/${repo_name}/contributors?per_page=100" 2>/dev/null || echo "[]")
    repo_contributors=$(echo "$contributors" | jq -r '.[].login // empty' 2>/dev/null || true)
    contributor_set="${contributor_set}${repo_contributors}"$'\n'
done
unique_contributors=$(echo "$contributor_set" | sort -u | grep -c '.' || echo "0")

# Get total commits (sum of contributions across all repos)
total_commits=0
for repo_name in $(echo "$repos_json" | jq -r '.[].name'); do
    contrib_json=$(api_get "https://api.github.com/repos/${ORG}/${repo_name}/contributors?per_page=100&anon=1" 2>/dev/null || echo "[]")
    repo_commits=$(echo "$contrib_json" | jq '[.[].contributions // 0] | add // 0' 2>/dev/null || echo "0")
    total_commits=$((total_commits + repo_commits))
done

# Get open PRs
open_prs=$(api_get "https://api.github.com/search/issues?q=org:${ORG}+type:pr+state:open" | jq '.total_count // 0')

# Get closed issues
closed_issues=$(api_get "https://api.github.com/search/issues?q=org:${ORG}+type:issue+state:closed" | jq '.total_count // 0')

# Write stats.json
cat > "${FILES_DIR}/stats.json" << EOF
{
  "repositories": ${repo_count},
  "contributors": ${unique_contributors},
  "total_commits": ${total_commits},
  "open_prs": ${open_prs},
  "closed_issues": ${closed_issues},
  "total_stars": ${total_stars}
}
EOF
echo "Updated ${FILES_DIR}/stats.json"

# --- Projects stars ---
echo "Updating project stars..."

projects_file="${FILES_DIR}/projects.json"
if [ -f "$projects_file" ]; then
    project_count=$(jq 'length' "$projects_file")
    for i in $(seq 0 $((project_count - 1))); do
        github_url=$(jq -r ".[$i].github_url" "$projects_file")
        # Extract owner/repo from URL
        repo_path=$(echo "$github_url" | sed 's|https://github.com/||')
        if [ -n "$repo_path" ] && [ "$repo_path" != "$github_url" ]; then
            stars=$(api_get "https://api.github.com/repos/${repo_path}" 2>/dev/null | jq '.stargazers_count // 0' 2>/dev/null || echo "0")
            # Update stars in projects.json
            tmp=$(mktemp)
            jq ".[$i].stars = ${stars}" "$projects_file" > "$tmp" && mv "$tmp" "$projects_file"
            echo "  ${repo_path}: ${stars} stars"
        fi
    done
    echo "Updated ${projects_file}"
fi

echo "=== Done ==="
