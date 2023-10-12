<div align="center">
</div>

<br/>

<div align="center">
  <img alt="npm downloads" src="https://img.shields.io/npm/dw/@candlefinance/@candlefinance/cache?logo=npm&label=NPM%20downloads&cacheSeconds=3600"/>
  <a alt="discord users online" href="https://discord.gg/qnAgjxhg6n" 
  target="_blank"
  rel="noopener noreferrer">
    <img alt="discord users online" src="https://img.shields.io/discord/986610142768406548?label=Discord&logo=discord&logoColor=white&cacheSeconds=3600"/>
</div>

<br/>

<h1 align="center">
   Sqlite key/value store for React Native 
</h1>

<br/>

Using [Room](https://developer.android.com/training/data-storage/room) for Android and [GRDB.swift](https://github.com/groue/GRDB.swift) for iOS, this library provides a simple interface to store data in a key-value format. The motivation for this library is to provide a simple interface to store data in a key-value format that is cross-platform and performant for offline storage.

## Installation

- Requires `iOS 15+` for iOS
- `compileSdkVersion = 34` and `Zullu 17` or higher for Android

```sh
yarn add @candlefinance/cache
```

```sh
npm i @candlefinance/cache
```

## Usage

```js
await write('key', 'value');

const result = await read('key'); // can be undefined if key doesn't exist

await remove('key');

await clear();
```

## Contributing

Join our [Discord](https://discord.gg/qnAgjxhg6n) and ask questions in the **#oss** channel.

## License

MIT
