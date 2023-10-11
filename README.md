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
   Sqlite offline store for React Native 
</h1>

<br/>

Using [Room](https://developer.android.com/training/data-storage/room) for Android and [GRDB.swift](https://github.com/groue/GRDB.swift) for iOS, this library provides a simple interface to store data in a key-value format.

## Installation

Requires `iOS 15+` in Podfile and `compileSdkVersion = 34` or higher in `android/app/build.gradle`.

```sh
yarn add @candlefinance/cache
```

## Usage

```js
const result = await write('key', 'value');

const result = await read('key');

const result = await remove('key');

const result = await clear();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
