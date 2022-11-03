import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UserSaleAccountChadFormService, UserSaleAccountChadFormGroup } from './user-sale-account-chad-form.service';
import { IUserSaleAccountChad } from '../user-sale-account-chad.model';
import { UserSaleAccountChadService } from '../service/user-sale-account-chad.service';
import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

@Component({
  selector: 'jhi-user-sale-account-chad-update',
  templateUrl: './user-sale-account-chad-update.component.html',
})
export class UserSaleAccountChadUpdateComponent implements OnInit {
  isSaving = false;
  userSaleAccount: IUserSaleAccountChad | null = null;
  userSaleAccountStatutValues = Object.keys(UserSaleAccountStatut);

  editForm: UserSaleAccountChadFormGroup = this.userSaleAccountFormService.createUserSaleAccountChadFormGroup();

  constructor(
    protected userSaleAccountService: UserSaleAccountChadService,
    protected userSaleAccountFormService: UserSaleAccountChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSaleAccount }) => {
      this.userSaleAccount = userSaleAccount;
      if (userSaleAccount) {
        this.updateForm(userSaleAccount);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userSaleAccount = this.userSaleAccountFormService.getUserSaleAccountChad(this.editForm);
    if (userSaleAccount.id !== null) {
      this.subscribeToSaveResponse(this.userSaleAccountService.update(userSaleAccount));
    } else {
      this.subscribeToSaveResponse(this.userSaleAccountService.create(userSaleAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSaleAccountChad>>): void {
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

  protected updateForm(userSaleAccount: IUserSaleAccountChad): void {
    this.userSaleAccount = userSaleAccount;
    this.userSaleAccountFormService.resetForm(this.editForm, userSaleAccount);
  }
}
