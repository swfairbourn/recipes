export class RatingModel {
    id: number;
    selected: boolean;
    value: number;

    constructor(id: number, selected: boolean, value: number) {
        this.id = id;
        this.selected = selected;
        this.value = value;
    }
}