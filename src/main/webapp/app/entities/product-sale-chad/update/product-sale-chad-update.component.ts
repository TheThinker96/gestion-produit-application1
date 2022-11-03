import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductSaleChadFormService, ProductSaleChadFormGroup } from './product-sale-chad-form.service';
import { IProductSaleChad } from '../product-sale-chad.model';
import { ProductSaleChadService } from '../service/product-sale-chad.service';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';
import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { StockProductChadService } from 'app/entities/stock-product-chad/service/stock-product-chad.service';
import { IUserSaleAccountChad } from 'app/entities/user-sale-account-chad/user-sale-account-chad.model';
import { UserSaleAccountChadService } from 'app/entities/user-sale-account-chad/service/user-sale-account-chad.service';

@Component({
  selector: 'jhi-product-sale-chad-update',
  templateUrl: './product-sale-chad-update.component.html',
})
export class ProductSaleChadUpdateComponent implements OnInit {
  isSaving = false;
  productSale: IProductSaleChad | null = null;

  productsSharedCollection: IProductChad[] = [];
  stockProductsSharedCollection: IStockProductChad[] = [];
  userSaleAccountsSharedCollection: IUserSaleAccountChad[] = [];

  editForm: ProductSaleChadFormGroup = this.productSaleFormService.createProductSaleChadFormGroup();

  constructor(
    protected productSaleService: ProductSaleChadService,
    protected productSaleFormService: ProductSaleChadFormService,
    protected productService: ProductChadService,
    protected stockProductService: StockProductChadService,
    protected userSaleAccountService: UserSaleAccountChadService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProductChad = (o1: IProductChad | null, o2: IProductChad | null): boolean => this.productService.compareProductChad(o1, o2);

  compareStockProductChad = (o1: IStockProductChad | null, o2: IStockProductChad | null): boolean =>
    this.stockProductService.compareStockProductChad(o1, o2);

  compareUserSaleAccountChad = (o1: IUserSaleAccountChad | null, o2: IUserSaleAccountChad | null): boolean =>
    this.userSaleAccountService.compareUserSaleAccountChad(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSale }) => {
      this.productSale = productSale;
      if (productSale) {
        this.updateForm(productSale);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSale = this.productSaleFormService.getProductSaleChad(this.editForm);
    if (productSale.id !== null) {
      this.subscribeToSaveResponse(this.productSaleService.update(productSale));
    } else {
      this.subscribeToSaveResponse(this.productSaleService.create(productSale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSaleChad>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productSale: IProductSaleChad): void {
    this.productSale = productSale;
    this.productSaleFormService.resetForm(this.editForm, productSale);

    this.productsSharedCollection = this.productService.addProductChadToCollectionIfMissing<IProductChad>(
      this.productsSharedCollection,
      productSale.product
    );
    this.stockProductsSharedCollection = this.stockProductService.addStockProductChadToCollectionIfMissing<IStockProductChad>(
      this.stockProductsSharedCollection,
      productSale.stockProduct
    );
    this.userSaleAccountsSharedCollection = this.userSaleAccountService.addUserSaleAccountChadToCollectionIfMissing<IUserSaleAccountChad>(
      this.userSaleAccountsSharedCollection,
      productSale.userSaleAccount
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProductChad[]>) => res.body ?? []))
      .pipe(
        map((products: IProductChad[]) =>
          this.productService.addProductChadToCollectionIfMissing<IProductChad>(products, this.productSale?.product)
        )
      )
      .subscribe((products: IProductChad[]) => (this.productsSharedCollection = products));

    this.stockProductService
      .query()
      .pipe(map((res: HttpResponse<IStockProductChad[]>) => res.body ?? []))
      .pipe(
        map((stockProducts: IStockProductChad[]) =>
          this.stockProductService.addStockProductChadToCollectionIfMissing<IStockProductChad>(
            stockProducts,
            this.productSale?.stockProduct
          )
        )
      )
      .subscribe((stockProducts: IStockProductChad[]) => (this.stockProductsSharedCollection = stockProducts));

    this.userSaleAccountService
      .query()
      .pipe(map((res: HttpResponse<IUserSaleAccountChad[]>) => res.body ?? []))
      .pipe(
        map((userSaleAccounts: IUserSaleAccountChad[]) =>
          this.userSaleAccountService.addUserSaleAccountChadToCollectionIfMissing<IUserSaleAccountChad>(
            userSaleAccounts,
            this.productSale?.userSaleAccount
          )
        )
      )
      .subscribe((userSaleAccounts: IUserSaleAccountChad[]) => (this.userSaleAccountsSharedCollection = userSaleAccounts));
  }
}
