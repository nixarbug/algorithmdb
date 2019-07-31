import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ProblemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-problem div table .btn-danger'));
  title = element.all(by.css('jhi-problem div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class ProblemUpdatePage {
  pageTitle = element(by.id('jhi-problem-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  dateUpdatedInput = element(by.id('field_dateUpdated'));
  problemGroupSelect = element(by.id('field_problemGroup'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setDateCreatedInput(dateCreated) {
    await this.dateCreatedInput.sendKeys(dateCreated);
  }

  async getDateCreatedInput() {
    return await this.dateCreatedInput.getAttribute('value');
  }

  async setDateUpdatedInput(dateUpdated) {
    await this.dateUpdatedInput.sendKeys(dateUpdated);
  }

  async getDateUpdatedInput() {
    return await this.dateUpdatedInput.getAttribute('value');
  }

  async problemGroupSelectLastOption(timeout?: number) {
    await this.problemGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async problemGroupSelectOption(option) {
    await this.problemGroupSelect.sendKeys(option);
  }

  getProblemGroupSelect(): ElementFinder {
    return this.problemGroupSelect;
  }

  async getProblemGroupSelectedOption() {
    return await this.problemGroupSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProblemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-problem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-problem'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
