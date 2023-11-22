export class TagModel {
    id: number;
    selected: boolean;
    value: String;

    constructor(id: number, selected: boolean, value: String) {
        this.id = id;
        this.selected = selected;
        this.value = value;
    }
}