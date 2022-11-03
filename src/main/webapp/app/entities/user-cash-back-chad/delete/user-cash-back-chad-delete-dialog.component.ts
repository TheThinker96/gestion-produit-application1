import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserCashBackChad } from '../user-cash-back-chad.model';
import { UserCashBackChadService } from '../service/user-cash-back-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-cash-back-chad-delete-dialog.component.html',
})
export class UserCashBackChadDeleteDialogComponent {
  userCashBack?: IUserCashBackChad;

  constructor(protected userCashBackService: UserCashBackChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userCashBackService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
