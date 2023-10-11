import * as React from 'react';

import { StyleSheet, View, Text, Pressable } from 'react-native';
import { clear, read, remove, write } from '@candlefinance/cache';

export default function App() {
  const [cacheValue, setCacheValue] = React.useState<string>('yoo');

  return (
    <View style={styles.container}>
      <View style={{ height: 30 }} />
      <Text>{cacheValue}</Text>
      <View style={{ height: 30 }} />
      <Pressable
        onPress={async () => {
          console.log('write');
          const v = await write('key', 'Hello World');
          console.log(v);
        }}
      >
        <Text>Write</Text>
      </Pressable>
      <View style={{ height: 30 }} />
      <Pressable
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
      </Pressable>
      <View style={{ height: 30 }} />
      <Pressable
        onPress={async () => {
          const v = await remove('key');
          console.log('remove', v);
          console.log('removed', v);
        }}
      >
        <Text>Remove</Text>
      </Pressable>
      <View style={{ height: 30 }} />
      <Pressable
        onPress={async () => {
          const v = await clear();
          console.log('clear', v);
        }}
      >
        <Text>clear</Text>
      </Pressable>
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
