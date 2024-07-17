import { Injectable } from '@angular/core';
import { IMessage } from '@stomp/stompjs';
import { RxStomp } from '@stomp/rx-stomp';
@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client: RxStomp | undefined;

  constructor() {}

  connect(): void {
    this.client = new RxStomp();
    const config = {
      brokerURL: 'ws://localhost:8080/ws',
    };
    this.client.configure(config);
    this.client.activate();
  }

  disconnect(): void {
    this.client?.deactivate();
  }

  subscribeToTopic(topic: string): void {
    this.client?.watch(topic).subscribe((message: IMessage) => {
      console.log('Subscribed for Messages');
      console.log(message.body);
    });
    console.log(`User is subscribed to ${topic}`);
  }

  sendMessage(message: any): void {
    this.client?.publish({ destination: '/app/hello', body: message });
  }
}
