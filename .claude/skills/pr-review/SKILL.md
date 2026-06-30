---
name: pr-review
description: Review a GitHub pull request for correctness, security, and quality. Fetches the PR diff via the gh CLI, analyzes the changes against this project's conventions, and produces a structured review with severity-ranked findings. Use when asked to review a PR, review a branch's changes, or check a diff before merge.
---

# Pull Request Review

Produce a thorough, actionable review of a pull request.

## 1. Resolve the target

Determine what to review, in this order:
- An explicit PR number or URL the user gave (`gh pr view <n>`).
- Otherwise the PR for the current branch: `gh pr view --json number,title,body,headRefName,baseRefName`.
- If there is no open PR, review the diff of the current branch against `main`.

Gather context:
```bash
gh pr view <n> --json title,body,author,headRefName,baseRefName,additions,deletions,files
gh pr diff <n>                       # full diff
gh pr diff <n> --name-only           # changed files
```
For a local branch with no PR: `git diff main...HEAD`.

Read the full diff. For any non-trivial change, open the surrounding files (not just the diff hunks) so you understand the context a line lives in before judging it.

## 2. What to look for

Evaluate the changes across these dimensions. Skip dimensions that don't apply.

- **Correctness** — logic errors, off-by-one, null/Optional handling, incorrect conditionals, broken edge cases, concurrency issues, resource leaks (unclosed streams/connections).
- **Security** — injection (SQL/JPQL), missing authz/authn checks, secrets committed in code or config, unvalidated input, sensitive data in logs. Flag any hardcoded API keys or secrets — these belong in environment variables.
- **API & data contracts** — breaking changes to REST endpoints, DTOs, or JPA entities; pagination/sorting correctness; migration/schema implications (`ddl-auto` is create-drop here, so entity changes reset the H2 schema).
- **Tests** — are new code paths covered? Do tests assert behavior, not just run it?
- **Spring conventions** — proper use of `@Transactional`, constructor injection over field injection, layering (controller → service → repository), exception handling.
- **Quality** — duplication, dead code, naming, unnecessary complexity, missing null-safety, things that could reuse existing helpers.

## 3. Verify before asserting

Do not report a finding you haven't grounded. For each suspected issue, confirm by reading the relevant code. If a claim depends on runtime behavior, say it's unverified rather than stating it as fact. Prefer a few high-confidence findings over many speculative ones.

## 4. Output format

Start with a 2–3 sentence summary and an overall recommendation: **Approve**, **Approve with comments**, or **Request changes**.

Then list findings grouped by severity. For each:

> **[Critical|High|Medium|Low]** `path/to/File.java:line` — concise description.
> Why it matters, and the suggested fix (include a code snippet when helpful).

Severity guide:
- **Critical** — bugs that break functionality, data loss, or security holes. Must fix before merge.
- **High** — likely bugs or significant design problems.
- **Medium** — quality/maintainability issues worth addressing.
- **Low** — nits, style, optional suggestions.

End with a short list of what's done well, when warranted.

## 5. Optional: post the review

Only if the user explicitly asks, post the findings to GitHub:
- Inline comments / overall review: `gh pr review <n> --comment --body "..."` (use `--request-changes` or `--approve` only when the user says so).
- Confirm with the user before posting anything outward-facing.

Default to printing the review in the conversation, not posting it.
