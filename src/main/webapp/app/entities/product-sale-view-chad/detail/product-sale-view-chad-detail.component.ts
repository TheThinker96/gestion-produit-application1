import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSaleViewChad } from '../product-sale-view-chad.model';

@Component({
  selector: 'jhi-product-sale-view-chad-detail',
  templateUrl: './product-sale-view-chad-detail.component.html',
})
export class ProductSaleViewChadDetailComponent implements OnInit {
  productSaleView: IProductSaleViewChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSaleView }) => {
      this.productSaleView = productSaleView;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
