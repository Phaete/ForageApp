import {CustomMarker} from "./CustomMarker.ts";
import {ForageWikiItem} from "./ForageWikiItem.ts";

export type ForageMapItem = {
	id: string
	forageWikiItem: ForageWikiItem
	customMarker: CustomMarker
	position: number[]
	quantity: string
	quality: string
	notes: string
}