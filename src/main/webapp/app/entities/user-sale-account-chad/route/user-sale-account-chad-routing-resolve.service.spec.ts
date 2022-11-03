import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUserSaleAccountChad } from '../user-sale-account-chad.model';
import { UserSaleAccountChadService } from '../service/user-sale-account-chad.service';

import { UserSaleAccountChadRoutingResolveService } from './user-sale-account-chad-routing-resolve.service';

describe('UserSaleAccountChad routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserSaleAccountChadRoutingResolveService;
  let service: UserSaleAccountChadService;
  let resultUserSaleAccountChad: IUserSaleAccountChad | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(UserSaleAccountChadRoutingResolveService);
    service = TestBed.inject(UserSaleAccountChadService);
    resultUserSaleAccountChad = undefined;
  });

  describe('resolve', () => {
    it('should return IUserSaleAccountChad returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountChad = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserSaleAccountChad).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountChad = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserSaleAccountChad).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IUserSaleAccountChad>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountChad = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserSaleAccountChad).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
