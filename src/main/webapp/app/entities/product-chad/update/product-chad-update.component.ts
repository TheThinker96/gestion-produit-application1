import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProductChadFormService, ProductChadFormGroup } from './product-chad-form.service';
import { IProductChad } from '../product-chad.model';
import { ProductChadService } from '../service/product-chad.service';

@Component({
  selector: 'jhi-product-chad-update',
  templateUrl: './product-chad-update.component.html',
})
export class ProductChadUpdateComponent implements OnInit {
  isSaving = false;
  product: IProductChad | null = null;

  editForm: ProductChadFormGroup = this.productFormService.createProductChadFormGroup();

  constructor(
    protected productService: ProductChadService,
    protected productFormService: ProductChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      if (product) {
        this.updateForm(product);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.productFormService.getProductChad(this.editForm);
    if (product.id !== null) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductChad>>): void {
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

  protected updateForm(product: IProductChad): void {
    this.product = product;
    this.productFormService.resetForm(this.editForm, product);
  }
}
