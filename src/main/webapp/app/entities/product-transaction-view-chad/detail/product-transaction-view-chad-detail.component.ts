import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';

@Component({
  selector: 'jhi-product-transaction-view-chad-detail',
  templateUrl: './product-transaction-view-chad-detail.component.html',
})
export class ProductTransactionViewChadDetailComponent implements OnInit {
  productTransactionView: IProductTransactionViewChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransactionView }) => {
      this.productTransactionView = productTransactionView;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
