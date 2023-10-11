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

export function multiply(a: number, b: number): Promise<number> {
  return Cache.multiply(a, b);
}

export function write(key: string, value: string): Promise<boolean> {
  return Cache.write(key, value);
}

export function read(key: string): Promise<string> {
  return Cache.read(key);
}

export function remove(key: string): Promise<boolean> {
  return Cache.delete(key);
}

export function clear(): Promise<boolean> {
  return Cache.clear();
}
