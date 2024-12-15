# Contributing Guidelines

Thank you for your interest in contributing to our Online Banking System! Here are some guidelines to help you get started.

## Branch Strategy

- `main`: Production-ready code
- `develop`: Development branch, all feature branches merge here first
- `feature/*`: New features (e.g., `feature/add-transaction-history`)
- `bugfix/*`: Bug fixes (e.g., `bugfix/fix-concurrent-transfer`)

## Development Workflow

1. Create a new branch from `develop`:
```bash
git checkout develop
git pull origin develop
git checkout -b feature/your-feature-name
```

2. Make your changes and commit them:
```bash
git add .
git commit -m "Description of your changes"
```

3. Keep your branch updated:
```bash
git checkout develop
git pull origin develop
git checkout feature/your-feature-name
git merge develop
```

4. Push your changes:
```bash
git push origin feature/your-feature-name
```

5. Create a Pull Request on GitHub

## Code Style Guidelines

- Follow Java naming conventions
- Add comments for complex logic
- Write unit tests for new features
- Maintain code coverage above 50%

## Pull Request Process

1. Update the README.md if needed
2. Update the documentation
3. Ensure all tests pass
4. Get at least one code review
5. Squash commits before merging

## Testing

- Run all tests before submitting PR:
```bash
mvn clean test
```

- Check code coverage:
```bash
mvn clean test
open target/site/jacoco/index.html
``` 