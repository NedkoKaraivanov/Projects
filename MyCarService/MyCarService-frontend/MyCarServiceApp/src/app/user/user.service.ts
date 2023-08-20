import { Injectable, OnDestroy } from '@angular/core';
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

  get isLogged(): boolean {
    return !!this.user;
  }

  get isAnonymous(): boolean {
    return !!this.user ? false : true;
  }

  get isAdmin(): boolean {
    return !!this.user?.roles.includes('2');
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
      .pipe(tap((user) => this.user$$.next(user)));
  }

  updateProfile(formValue: {}) {
    return this.http
      .put<User>(this.profile_url, formValue)
      .pipe(tap((user) => this.user$$.next(user)));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
