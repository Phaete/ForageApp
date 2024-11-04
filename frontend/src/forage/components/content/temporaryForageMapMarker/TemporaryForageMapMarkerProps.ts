import {GeoPosition} from "../../../types/GeoPosition.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";

export type TemporaryForageMapMarkerProps = {
	position: GeoPosition
	fetchForageMapItems: () => void
	forageWikiItems: ForageWikiItem[]
	customMarker: CustomMarker[]
	setAddForageMapItem: (addForageMapItem: boolean) => void
}