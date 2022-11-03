import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProductSaleViewChadFormService, ProductSaleViewChadFormGroup } from './product-sale-view-chad-form.service';
import { IProductSaleViewChad } from '../product-sale-view-chad.model';
import { ProductSaleViewChadService } from '../service/product-sale-view-chad.service';

@Component({
  selector: 'jhi-product-sale-view-chad-update',
  templateUrl: './product-sale-view-chad-update.component.html',
})
export class ProductSaleViewChadUpdateComponent implements OnInit {
  isSaving = false;
  productSaleView: IProductSaleViewChad | null = null;

  editForm: ProductSaleViewChadFormGroup = this.productSaleViewFormService.createProductSaleViewChadFormGroup();

  constructor(
    protected productSaleViewService: ProductSaleViewChadService,
    protected productSaleViewFormService: ProductSaleViewChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSaleView }) => {
      this.productSaleView = productSaleView;
      if (productSaleView) {
        this.updateForm(productSaleView);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSaleView = this.productSaleViewFormService.getProductSaleViewChad(this.editForm);
    if (productSaleView.id !== null) {
      this.subscribeToSaveResponse(this.productSaleViewService.update(productSaleView));
    } else {
      this.subscribeToSaveResponse(this.productSaleViewService.create(productSaleView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSaleViewChad>>): void {
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

  protected updateForm(productSaleView: IProductSaleViewChad): void {
    this.productSaleView = productSaleView;
    this.productSaleViewFormService.resetForm(this.editForm, productSaleView);
  }
}
