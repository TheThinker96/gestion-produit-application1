import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductTransactionChadFormService } from './product-transaction-chad-form.service';
import { ProductTransactionChadService } from '../service/product-transaction-chad.service';
import { IProductTransactionChad } from '../product-transaction-chad.model';
import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { StockProductChadService } from 'app/entities/stock-product-chad/service/stock-product-chad.service';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';

import { ProductTransactionChadUpdateComponent } from './product-transaction-chad-update.component';

describe('ProductTransactionChad Management Update Component', () => {
  let comp: ProductTransactionChadUpdateComponent;
  let fixture: ComponentFixture<ProductTransactionChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productTransactionFormService: ProductTransactionChadFormService;
  let productTransactionService: ProductTransactionChadService;
  let stockProductService: StockProductChadService;
  let productService: ProductChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductTransactionChadUpdateComponent],
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
      .overrideTemplate(ProductTransactionChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductTransactionChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productTransactionFormService = TestBed.inject(ProductTransactionChadFormService);
    productTransactionService = TestBed.inject(ProductTransactionChadService);
    stockProductService = TestBed.inject(StockProductChadService);
    productService = TestBed.inject(ProductChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call StockProductChad query and add missing value', () => {
      const productTransaction: IProductTransactionChad = { id: 456 };
      const stockProduct: IStockProductChad = { id: 81900 };
      productTransaction.stockProduct = stockProduct;

      const stockProductCollection: IStockProductChad[] = [{ id: 87090 }];
      jest.spyOn(stockProductService, 'query').mockReturnValue(of(new HttpResponse({ body: stockProductCollection })));
      const additionalStockProductChads = [stockProduct];
      const expectedCollection: IStockProductChad[] = [...additionalStockProductChads, ...stockProductCollection];
      jest.spyOn(stockProductService, 'addStockProductChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(stockProductService.query).toHaveBeenCalled();
      expect(stockProductService.addStockProductChadToCollectionIfMissing).toHaveBeenCalledWith(
        stockProductCollection,
        ...additionalStockProductChads.map(expect.objectContaining)
      );
      expect(comp.stockProductsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProductChad query and add missing value', () => {
      const productTransaction: IProductTransactionChad = { id: 456 };
      const product: IProductChad = { id: 54457 };
      productTransaction.product = product;

      const productCollection: IProductChad[] = [{ id: 99851 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProductChads = [product];
      const expectedCollection: IProductChad[] = [...additionalProductChads, ...productCollection];
      jest.spyOn(productService, 'addProductChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductChadToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProductChads.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productTransaction: IProductTransactionChad = { id: 456 };
      const stockProduct: IStockProductChad = { id: 53591 };
      productTransaction.stockProduct = stockProduct;
      const product: IProductChad = { id: 62796 };
      productTransaction.product = product;

      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      expect(comp.stockProductsSharedCollection).toContain(stockProduct);
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.productTransaction).toEqual(productTransaction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionChad>>();
      const productTransaction = { id: 123 };
      jest.spyOn(productTransactionFormService, 'getProductTransactionChad').mockReturnValue(productTransaction);
      jest.spyOn(productTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransaction }));
      saveSubject.complete();

      // THEN
      expect(productTransactionFormService.getProductTransactionChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productTransactionService.update).toHaveBeenCalledWith(expect.objectContaining(productTransaction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionChad>>();
      const productTransaction = { id: 123 };
      jest.spyOn(productTransactionFormService, 'getProductTransactionChad').mockReturnValue({ id: null });
      jest.spyOn(productTransactionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransaction }));
      saveSubject.complete();

      // THEN
      expect(productTransactionFormService.getProductTransactionChad).toHaveBeenCalled();
      expect(productTransactionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionChad>>();
      const productTransaction = { id: 123 };
      jest.spyOn(productTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productTransactionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStockProductChad', () => {
      it('Should forward to stockProductService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(stockProductService, 'compareStockProductChad');
        comp.compareStockProductChad(entity, entity2);
        expect(stockProductService.compareStockProductChad).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProductChad', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProductChad');
        comp.compareProductChad(entity, entity2);
        expect(productService.compareProductChad).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
