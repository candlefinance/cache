import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package '@candlefinance/cache' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const Cache = NativeModules.KitCacheManager
  ? NativeModules.KitCacheManager
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export async function write(key: string, value: string) {
  await Cache.write(key, value);
}

export async function read(key: string): Promise<string | undefined> {
  const result = await Cache.read(key);
  return result === null ? undefined : result;
}

export async function remove(key: string) {
  await Cache.delete(key);
}

export async function clear() {
  await Cache.clear();
}

export default { write, read, remove, clear };
