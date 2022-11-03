import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSaleViewChad } from '../product-sale-view-chad.model';
import { ProductSaleViewChadService } from '../service/product-sale-view-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-sale-view-chad-delete-dialog.component.html',
})
export class ProductSaleViewChadDeleteDialogComponent {
  productSaleView?: IProductSaleViewChad;

  constructor(protected productSaleViewService: ProductSaleViewChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productSaleViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
