import {CustomMarker} from "./CustomMarker.ts";
import {ForageWikiItem} from "./ForageWikiItem.ts";
import {ForageMapItemAssessment} from "./ForageMapItemAssessment.ts";
import {GeoPosition} from "./GeoPosition.ts";
import {ForageMapItemOwnership} from "./ForageMapItemOwnership.ts";

export type ForageMapItem = {
	id: string
	forageWikiItem: ForageWikiItem
	customMarker: CustomMarker
	position: GeoPosition
	ownership: ForageMapItemOwnership
	assessment: ForageMapItemAssessment
	notes: string
}