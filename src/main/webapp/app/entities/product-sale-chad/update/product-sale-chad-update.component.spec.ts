import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSaleChadFormService } from './product-sale-chad-form.service';
import { ProductSaleChadService } from '../service/product-sale-chad.service';
import { IProductSaleChad } from '../product-sale-chad.model';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';
import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { StockProductChadService } from 'app/entities/stock-product-chad/service/stock-product-chad.service';
import { IUserSaleAccountChad } from 'app/entities/user-sale-account-chad/user-sale-account-chad.model';
import { UserSaleAccountChadService } from 'app/entities/user-sale-account-chad/service/user-sale-account-chad.service';

import { ProductSaleChadUpdateComponent } from './product-sale-chad-update.component';

describe('ProductSaleChad Management Update Component', () => {
  let comp: ProductSaleChadUpdateComponent;
  let fixture: ComponentFixture<ProductSaleChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSaleFormService: ProductSaleChadFormService;
  let productSaleService: ProductSaleChadService;
  let productService: ProductChadService;
  let stockProductService: StockProductChadService;
  let userSaleAccountService: UserSaleAccountChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSaleChadUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProductSaleChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSaleChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSaleFormService = TestBed.inject(ProductSaleChadFormService);
    productSaleService = TestBed.inject(ProductSaleChadService);
    productService = TestBed.inject(ProductChadService);
    stockProductService = TestBed.inject(StockProductChadService);
    userSaleAccountService = TestBed.inject(UserSaleAccountChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductChad query and add missing value', () => {
      const productSale: IProductSaleChad = { id: 456 };
      const product: IProductChad = { id: 36296 };
      productSale.product = product;

      const productCollection: IProductChad[] = [{ id: 28926 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProductChads = [product];
      const expectedCollection: IProductChad[] = [...additionalProductChads, ...productCollection];
      jest.spyOn(productService, 'addProductChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductChadToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProductChads.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call StockProductChad query and add missing value', () => {
      const productSale: IProductSaleChad = { id: 456 };
      const stockProduct: IStockProductChad = { id: 61402 };
      productSale.stockProduct = stockProduct;

      const stockProductCollection: IStockProductChad[] = [{ id: 67639 }];
      jest.spyOn(stockProductService, 'query').mockReturnValue(of(new HttpResponse({ body: stockProductCollection })));
      const additionalStockProductChads = [stockProduct];
      const expectedCollection: IStockProductChad[] = [...additionalStockProductChads, ...stockProductCollection];
      jest.spyOn(stockProductService, 'addStockProductChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      expect(stockProductService.query).toHaveBeenCalled();
      expect(stockProductService.addStockProductChadToCollectionIfMissing).toHaveBeenCalledWith(
        stockProductCollection,
        ...additionalStockProductChads.map(expect.objectContaining)
      );
      expect(comp.stockProductsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserSaleAccountChad query and add missing value', () => {
      const productSale: IProductSaleChad = { id: 456 };
      const userSaleAccount: IUserSaleAccountChad = { id: 11573 };
      productSale.userSaleAccount = userSaleAccount;

      const userSaleAccountCollection: IUserSaleAccountChad[] = [{ id: 85576 }];
      jest.spyOn(userSaleAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: userSaleAccountCollection })));
      const additionalUserSaleAccountChads = [userSaleAccount];
      const expectedCollection: IUserSaleAccountChad[] = [...additionalUserSaleAccountChads, ...userSaleAccountCollection];
      jest.spyOn(userSaleAccountService, 'addUserSaleAccountChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      expect(userSaleAccountService.query).toHaveBeenCalled();
      expect(userSaleAccountService.addUserSaleAccountChadToCollectionIfMissing).toHaveBeenCalledWith(
        userSaleAccountCollection,
        ...additionalUserSaleAccountChads.map(expect.objectContaining)
      );
      expect(comp.userSaleAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productSale: IProductSaleChad = { id: 456 };
      const product: IProductChad = { id: 40769 };
      productSale.product = product;
      const stockProduct: IStockProductChad = { id: 55285 };
      productSale.stockProduct = stockProduct;
      const userSaleAccount: IUserSaleAccountChad = { id: 64627 };
      productSale.userSaleAccount = userSaleAccount;

      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.stockProductsSharedCollection).toContain(stockProduct);
      expect(comp.userSaleAccountsSharedCollection).toContain(userSaleAccount);
      expect(comp.productSale).toEqual(productSale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleChad>>();
      const productSale = { id: 123 };
      jest.spyOn(productSaleFormService, 'getProductSaleChad').mockReturnValue(productSale);
      jest.spyOn(productSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSale }));
      saveSubject.complete();

      // THEN
      expect(productSaleFormService.getProductSaleChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSaleService.update).toHaveBeenCalledWith(expect.objectContaining(productSale));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleChad>>();
      const productSale = { id: 123 };
      jest.spyOn(productSaleFormService, 'getProductSaleChad').mockReturnValue({ id: null });
      jest.spyOn(productSaleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSale: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSale }));
      saveSubject.complete();

      // THEN
      expect(productSaleFormService.getProductSaleChad).toHaveBeenCalled();
      expect(productSaleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleChad>>();
      const productSale = { id: 123 };
      jest.spyOn(productSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSaleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProductChad', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProductChad');
        comp.compareProductChad(entity, entity2);
        expect(productService.compareProductChad).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStockProductChad', () => {
      it('Should forward to stockProductService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(stockProductService, 'compareStockProductChad');
        comp.compareStockProductChad(entity, entity2);
        expect(stockProductService.compareStockProductChad).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUserSaleAccountChad', () => {
      it('Should forward to userSaleAccountService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userSaleAccountService, 'compareUserSaleAccountChad');
        comp.compareUserSaleAccountChad(entity, entity2);
        expect(userSaleAccountService.compareUserSaleAccountChad).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
