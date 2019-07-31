import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FunctionClassComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-function-class div table .btn-danger'));
  title = element.all(by.css('jhi-function-class div h2#page-heading span')).first();

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

export class FunctionClassUpdatePage {
  pageTitle = element(by.id('jhi-function-class-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  formulaInput = element(by.id('field_formula'));
  relativeOrderInput = element(by.id('field_relativeOrder'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setFormulaInput(formula) {
    await this.formulaInput.sendKeys(formula);
  }

  async getFormulaInput() {
    return await this.formulaInput.getAttribute('value');
  }

  async setRelativeOrderInput(relativeOrder) {
    await this.relativeOrderInput.sendKeys(relativeOrder);
  }

  async getRelativeOrderInput() {
    return await this.relativeOrderInput.getAttribute('value');
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

export class FunctionClassDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-functionClass-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-functionClass'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
