import { Injectable, OnDestroy } from '@angular/core';
import axios from 'axios';
import { User } from '../types/user';
import { BehaviorSubject, Observable, Subscription, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserService implements OnDestroy {
  register_url: string = 'http://localhost:8080/api/auth/register';
  login_url: string = 'http://localhost:8080/api/auth/authenticate';
  logout_url: string = 'http://localhost:8080/api/auth/logout';
  profile_url: string = 'http://localhost:8080/api/users/profile';

  private user$$ = new BehaviorSubject<User | undefined>(undefined);
  public user$ = this.user$$.asObservable();

  user: User | undefined;
  USER_KEY = '[user]';

  get isLogged(): boolean {
    return !!this.user;
  }

  subscription: Subscription;

  constructor(private http: HttpClient) {
    this.subscription = this.user$.subscribe((user) => {
      this.user = user;
    });
  }

  login(formValue: {}) {
    return this.http
      .post<User>(this.login_url, formValue)
      .pipe(tap((user) => this.user$$.next(user)));
  }

  register(formValue: {}) {
    return this.http.post<User>(this.register_url, formValue).pipe(
      tap((user) => {
        this.user$$.next(user);
      })
    );
  }

  logout() {
    return this.http
      .post<User>(this.logout_url, {})
      .pipe(tap(() => this.user$$.next(undefined)));
  }

  getProfile() {
    return this.http
    .get<User>(this.profile_url)
    .pipe(tap(user => this.user$$.next(user)));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
