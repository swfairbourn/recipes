export class TagModel {
    id: number;
    selected: boolean;
    value: string;

    constructor(id: number, selected: boolean, value: string) {
        this.id = id;
        this.selected = selected;
        this.value = value;
    }
}