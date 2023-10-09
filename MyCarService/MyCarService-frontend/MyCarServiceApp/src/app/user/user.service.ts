import { Injectable, OnDestroy } from '@angular/core';
import { User } from '../types/user';
import { BehaviorSubject, Observable, Subscription, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UpdateEmailDto } from '../types/updateEmailDto';

@Injectable({
  providedIn: 'root',
})
export class UserService implements OnDestroy {
  register_url: string = 'http://localhost:8080/api/auth/register';
  login_url: string = 'http://localhost:8080/api/auth/authenticate';
  logout_url: string = 'http://localhost:8080/api/auth/logout';
  profile_url: string = 'http://localhost:8080/api/users/profile';
  update_email_url: string = 'http://localhost:8080/api/users/update-email';

  private user$$ = new BehaviorSubject<User | undefined>(undefined);
  public user$ = this.user$$.asObservable();

  user: User | undefined;

  get isLogged(): boolean {
    return localStorage.getItem('isLogged') === 'true';
  }

  get isAnonymous(): boolean {
    return localStorage.getItem('isLogged') !== 'true';
  }

  get isAdmin(): boolean {
    return localStorage.getItem('roles')!.includes('2');
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
    localStorage.setItem('isLogged', '');
    localStorage.setItem('access_token', '');
    localStorage.setItem('refresh_token', '');
    localStorage.setItem('roles', '');
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

  updateEmail(data: UpdateEmailDto) {
    return this.http.patch<UpdateEmailDto>(this.update_email_url, data);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
