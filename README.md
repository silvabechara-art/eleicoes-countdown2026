# Eleições 2026 — Contagem Regressiva

Projeto Android completo para a contagem regressiva até **04/10/2026 às 08:00**, com relógio local e alarme diário às 08:00.

## Gerar APK pelo GitHub

1. Extraia este ZIP no computador.
2. Crie um repositório no GitHub.
3. Envie **todos os arquivos e pastas de dentro desta pasta**, e não o ZIP.
4. Confirme que `.github/workflows/build-apk.yml` aparece no repositório.
5. Abra **Actions**.
6. Entre em **Gerar APK**.
7. Clique em **Run workflow** se necessário.
8. Quando terminar com ✓ verde, abra a execução e baixe o artefato **EleicoesCountdown-debug-apk**.

## Recursos

- Contagem regressiva para 04/10/2026 08:00.
- Relógio local.
- Alarme diário às 08:00 até 04/10/2026.
- Som de sino e vibração.
- Reagendamento após reinicialização e alteração de horário/fuso.
- Solicitação de permissão para notificações e alarmes exatos em versões compatíveis.

## Observação

O APK Debug é apropriado para teste e instalação manual. O APK Release deste projeto é gerado sem assinatura; para distribuição, ele deve ser assinado com uma chave própria.
