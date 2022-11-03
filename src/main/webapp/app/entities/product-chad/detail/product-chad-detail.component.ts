import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductChad } from '../product-chad.model';

@Component({
  selector: 'jhi-product-chad-detail',
  templateUrl: './product-chad-detail.component.html',
})
export class ProductChadDetailComponent implements OnInit {
  product: IProductChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
