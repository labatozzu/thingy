# Domain Docs

This repository uses a single-context domain documentation layout.

## Before exploring

- Read `GLOSSARY.md` at the repository root when it exists.
- Read relevant decisions under `docs/adr/` when they exist.
- If either location does not exist, proceed without creating placeholder files.

## Layout

```text
/
├── GLOSSARY.md
├── docs/adr/
└── backend/
```

Use glossary terms consistently in issue titles, specifications, test names, and code. If a proposed change conflicts with an ADR, call out the conflict explicitly instead of silently overriding it.
