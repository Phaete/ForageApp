import {ForageMapItem} from "../../../types/ForageMapItem.ts";
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";
import {User} from "../../../types/User.ts";

export type DetailedForageMapItemCardProps = {
	forageWikiItems: ForageWikiItem[],
	customMarker: CustomMarker[],
	forageMapItem: ForageMapItem,
	fetchForageMapItems: () => void,
	setDetailedForageMapItem: (detailedForageMapItem: ForageMapItem | null) => void
	showDetailedForageMapItemDrawer: boolean
	setShowDetailedForageMapItemDrawer: (showDetailedForageMapItemDrawer: boolean) => void
	user: User | null
}