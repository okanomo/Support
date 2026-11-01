## Mandatory Rules for Claude Code
- Always respond in Japanese and write all code comments in Japanese.
- Do NOT refactor existing code unless clearly requested.
- Prefer minimal, localized changes over large improvements.
- Stability and existing behavior are more important than code cleanliness.


## Change Proposal Requirement
Before making any code changes:
- Explain what will be changed
- Explain why it is necessary
- Describe potential risks or side effects
- Wait for explicit approval before proceeding.

## Restricted Areas
The following areas must not be modified unless explicitly instructed:
- Authentication (Devise, sessions, confirmations)
- Payments (Stripe, WebhookController, BuyPointHistory)
- Point balance logic
- Rank calculation and cron jobs
- Production environment configuration


## Security Rules
- Never request or output secrets, API keys, or credentials.
- Do not log or print personal data.
- Assume production-like constraints even in development.


## Cost Awareness
- Keep responses concise.
- Avoid repeating large code blocks unless necessary.
- Prefer explanation over full implementation when possible.