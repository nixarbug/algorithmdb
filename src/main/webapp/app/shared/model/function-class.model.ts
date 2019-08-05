export interface IFunctionClass {
  id?: number;
  name?: string;
  formula?: any;
  formulaMarkdown?: any;
  relativeOrder?: number;
}

export class FunctionClass implements IFunctionClass {
  constructor(
    public id?: number,
    public name?: string,
    public formula?: any,
    public formulaMarkdown?: any,
    public relativeOrder?: number
  ) {}
}
