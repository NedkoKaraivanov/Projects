import { Injectable } from '@angular/core';
import axios from 'axios';
import { User } from '../types/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor() {}

  async register(data: {}) {
    const res = await axios.post<User>(
      'http://localhost:8080/api/auth/register',
      data
    );
  }

  async login(data: {}) {
    const res = await axios.post<User>(
      'http://localhost:8080/api/auth/authenticate',
      data
    );
    console.log(res);
    
  }
}
