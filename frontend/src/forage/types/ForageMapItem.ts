import {CustomMarker} from "./CustomMarker.ts";
import {ForageWikiItem} from "./ForageWikiItem.ts";
import {ForageMapItemAssessment} from "./ForageMapItemAssessment.ts";
import {GeoPosition} from "./GeoPosition.ts";

export type ForageMapItem = {
	id: string
	forageWikiItem: ForageWikiItem
	customMarker: CustomMarker
	position: GeoPosition
	assessment: ForageMapItemAssessment
	notes: string
}