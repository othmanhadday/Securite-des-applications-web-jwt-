import { TestBed } from '@angular/core/testing';

import { AuthenticationSeviceService } from './authentication-sevice.service';

describe('AuthenticationSeviceService', () => {
  let service: AuthenticationSeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthenticationSeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
