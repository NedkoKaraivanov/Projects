import { Injectable } from '@angular/core';
import { Message } from '@stomp/stompjs';
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
    this.client?.watch(topic).subscribe((message: Message) => {
      console.log(message.body);
    });
  }

  sendMessage(message: any): void {
    this.client?.publish({ destination: '/app/hello', body: message });
  }
}
