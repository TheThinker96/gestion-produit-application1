import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSaleChad } from '../product-sale-chad.model';

@Component({
  selector: 'jhi-product-sale-chad-detail',
  templateUrl: './product-sale-chad-detail.component.html',
})
export class ProductSaleChadDetailComponent implements OnInit {
  productSale: IProductSaleChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSale }) => {
      this.productSale = productSale;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
