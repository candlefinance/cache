<br/>

<div align="center">
   <a href="https://www.npmjs.com/package/@candlefinance%2Fcache">
  <img alt="npm downloads" src="https://img.shields.io/npm/dw/@candlefinance/cache?logo=npm&label=NPM%20downloads&cacheSeconds=3600"/>
   </a>
  
  <a alt="discord users online" href="https://discord.gg/qnAgjxhg6n" 
  target="_blank"
  rel="noopener noreferrer">
    <img alt="discord users online" src="https://img.shields.io/discord/986610142768406548?label=Discord&logo=discord&logoColor=white&cacheSeconds=3600"/>
    </a>
</div>
<br/>

<h1 align="center">
   Cache store for React Native 
</h1>

<br/>

Using DiskCache for Android and PINCache for iOS, this library provides a simple interface to store data in a key-value format for offline mode.

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
