# my-crm-service

顧客管理、認証・認可、決済基盤のサービスを想定した学習用のプロジェクトです。

## プロジェクト構成

- my-crm-admin  
  管理者ツール
- my-crm-auth  
  認証・認可サーバー  
  [my-oidc-provider](https://github.com/ssaito-git/my-oidc-provider) に依存しています。事前にローカルインストールされている必要があります。
- my-crm-service  
  サービスのリソースサーバー
