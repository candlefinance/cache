import * as React from 'react';

import { clear, read, remove, write } from '@candlefinance/cache';
import {
  Platform,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

const benchmark = async () => {
  const start = Date.now();
  for (let i = 0; i < 10000; i++) {
    await write(`key${i}`, `value${i}`);
  }
  const end = Date.now();
  console.log('write', end - start);

  const start2 = Date.now();
  for (let i = 0; i < 10000; i++) {
    await read(`key${i}`);
  }
  const end2 = Date.now();
  console.log('read', end2 - start2);

  const start3 = Date.now();
  for (let i = 0; i < 10000; i++) {
    await remove(`key${i}`);
  }
  const end3 = Date.now();
  console.log('remove', end3 - start3);

  console.log(
    `Summary: It took ${end - start}ms to write 10000 items, ${
      end2 - start2
    }ms to read 10000 items, and ${
      end3 - start3
    }ms to remove 10000 items on Platform ${Platform.OS}`
  );
};

export default function App() {
  const [cacheValue, setCacheValue] = React.useState<string>('yoo');

  return (
    <View style={styles.container}>
      <View style={{ height: 30 }} />
      <Text>{cacheValue}</Text>
      <View style={{ height: 30 }} />
      <TouchableOpacity
        onPress={async () => {
          console.log('write');
          const v = await write('key', 'Hello World');
          console.log(v);
        }}
      >
        <Text>Write</Text>
      </TouchableOpacity>
      <View style={{ height: 30 }} />
      <TouchableOpacity
        onPress={async () => {
          try {
            const value = await read('key');
            console.log('read', value);
            setCacheValue(value);
          } catch {
            setCacheValue('empty');
          }
        }}
      >
        <Text>Read</Text>
      </TouchableOpacity>
      <View style={{ height: 30 }} />
      <TouchableOpacity
        onPress={async () => {
          const v = await remove('key');
          console.log('remove', v);
          console.log('removed', v);
        }}
      >
        <Text>Remove</Text>
      </TouchableOpacity>
      <View style={{ height: 30 }} />
      <TouchableOpacity
        onPress={async () => {
          const v = await clear();
          console.log('clear', v);
        }}
      >
        <Text>clear</Text>
      </TouchableOpacity>
      <View style={{ height: 30 }} />
      <TouchableOpacity
        onPress={async () => {
          console.log('benchmark');
          await benchmark();
        }}
      >
        <Text>benchmark</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
