import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";

export type ForageWikiItemEditorProps = {
	fetchWikiData: () => void,
	forageWikiItemToEdit?: ForageWikiItem,
	setEditItem?: (editItem: boolean) => void
}