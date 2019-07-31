import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BlogEntryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-blog-entry div table .btn-danger'));
  title = element.all(by.css('jhi-blog-entry div h2#page-heading span')).first();

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

export class BlogEntryUpdatePage {
  pageTitle = element(by.id('jhi-blog-entry-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  titleInput = element(by.id('field_title'));
  contentInput = element(by.id('field_content'));
  dateCreatedInput = element(by.id('field_dateCreated'));
  dateUpdatedInput = element(by.id('field_dateUpdated'));
  tagSelect = element(by.id('field_tag'));
  blogSelect = element(by.id('field_blog'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return await this.titleInput.getAttribute('value');
  }

  async setContentInput(content) {
    await this.contentInput.sendKeys(content);
  }

  async getContentInput() {
    return await this.contentInput.getAttribute('value');
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

  async tagSelectLastOption(timeout?: number) {
    await this.tagSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tagSelectOption(option) {
    await this.tagSelect.sendKeys(option);
  }

  getTagSelect(): ElementFinder {
    return this.tagSelect;
  }

  async getTagSelectedOption() {
    return await this.tagSelect.element(by.css('option:checked')).getText();
  }

  async blogSelectLastOption(timeout?: number) {
    await this.blogSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async blogSelectOption(option) {
    await this.blogSelect.sendKeys(option);
  }

  getBlogSelect(): ElementFinder {
    return this.blogSelect;
  }

  async getBlogSelectedOption() {
    return await this.blogSelect.element(by.css('option:checked')).getText();
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

export class BlogEntryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-blogEntry-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-blogEntry'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
