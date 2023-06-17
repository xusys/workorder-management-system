import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.app',
  appName: 'work_order_system',
  webDir: 'build',
  server: {
    androidScheme: 'https',
    'cleartext': true
  }
};

export default config;
