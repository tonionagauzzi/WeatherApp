# WeatherApp - 天気予報アプリ

## 概要

WeatherAppは、日本の主要都市の天気情報をリアルタイムで確認できるシンプルなAndroidアプリケーションです。現在の天気状況、気温、湿度、風速などの基本的な気象情報を提供します。Material 3デザインを採用し、モダンで使いやすいUIを実現しています。

主な機能：
- 日本の主要都市（東京、大阪、札幌、福岡、名古屋）の天気情報表示
- 現在の気温、最高/最低気温の表示
- 天気状態（晴れ、曇り、雨など）の視覚的表示
- 湿度、風速、降水量などの詳細情報
- 都市の切り替え機能

## 開発環境

- 言語: Kotlin
- 最小SDK: 31 (Android 12)
- ターゲットSDK: 35
- アーキテクチャ: MVVM（Model-View-ViewModel）
- UI: Jetpack Compose
- ネットワーク通信: Retrofit, OkHttp
- JSON解析: Moshi
- 非同期処理: Kotlin Coroutines, Flow
- ビルドシステム: Gradle (Kotlin DSL)
- コード品質管理: ktlint
- テスト: JUnit, Robolectric, Screenshot Testing

## 詳細な仕様

### アプリ構造

```
com.vitantonio.nagauzzi.weatherapp/
├── model/             # データモデル
│   └── Weather.kt     # 天気情報のデータモデル
├── repository/        # データ取得層
│   ├── api/           # API通信関連
│   │   ├── GeocodingService.kt  # 地理情報API
│   │   ├── NetworkModule.kt     # ネットワーク設定
│   │   └── OpenMeteoService.kt  # 天気情報API
│   └── WeatherRepository.kt     # 天気情報リポジトリ
├── ui/                # UI関連
│   ├── component/     # Compose UIコンポーネント
│   └── theme/         # アプリのテーマ設定
├── viewmodel/         # ViewModelクラス
│   └── WeatherViewModel.kt  # 天気情報のViewModel
└── MainActivity.kt    # メインアクティビティ
```

### データフロー

1. `WeatherViewModel`が`WeatherRepository`を通じて天気データをリクエスト
2. `WeatherRepository`が都市名を`GeocodingService`に渡して位置情報（緯度・経度）を取得
3. 取得した位置情報を用いて`OpenMeteoService`から天気情報を取得
4. APIから取得したデータを`Weather`モデルに変換
5. 変換したデータを`StateFlow`を通じてUIに反映

### 外部APIとの連携

- **Open Meteo API**: 現在の天気情報と予報データを取得
- **Geocoding API**: 都市名から緯度・経度情報を取得

### エラーハンドリング

- ネットワーク接続エラー時には適切なエラーメッセージを表示
- APIから位置情報が取得できない場合は、主要都市のデフォルト座標を使用
- APIから天気情報が取得できない場合は、適切なエラーメッセージを表示

### UI/UX設計

- Material 3デザインガイドラインに準拠
- ダークモード対応
- エッジ・ツー・エッジのフルスクリーン表示
- 都市選択のためのドロップダウンメニュー
- 天気状態の直感的なアイコン表示
- データ読み込み中のローディング表示

### テスト

- JUnitを用いたユニットテスト
- Robolectricを用いたAndroidテスト
- UIのスクリーンショットテスト

### 将来の展望

- 位置情報サービスを利用した現在地の天気表示
- 地図を利用した任意の地点の天気表示
- 週間予報の表示機能
- お気に入り都市の保存機能
- 天気通知機能
- ウィジェット対応

## 作者
[@tonionagauzzi](https://x.com/tonionagauzzi)
