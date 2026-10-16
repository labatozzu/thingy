# Issue Tracker: GitHub

Issues and specs for this repository live as GitHub Issues. Use the `gh` CLI for issue operations.

## Conventions

- Create an issue with `gh issue create --title "..." --body "..."`.
- Read an issue with `gh issue view <number> --json number,title,body,labels,comments`.
- List issues with `gh issue list` and appropriate state or label filters.
- Comment with `gh issue comment <number> --body "..."`.
- Apply or remove labels with `gh issue edit <number> --add-label "..."` or `--remove-label "..."`.
- Close an issue with `gh issue close <number> --comment "..."`.

The repository remote is the source of truth for the GitHub repository. `gh` should infer it when commands run from this checkout.

## Pull requests as a triage surface

PRs are not treated as a separate triage request surface for this repository.

## Publishing

When a skill says to publish a spec or ticket, create a GitHub Issue. When a skill says to fetch a ticket, read it with `gh issue view`.
