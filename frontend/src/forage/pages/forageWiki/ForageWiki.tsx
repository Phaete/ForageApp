import {ForageWikiProps} from "./ForageWikiProps.ts"
import {ForageWikiItem} from "../../types/ForageWikiItem.ts";
import ExpandableCard from "../../components/content/expandableCard/ExpandableCard.tsx";
import ForageWikiItemEditor from "./forageWikiItemEditor/ForageWikiItemEditor.tsx";

export default function ForageWiki(props: Readonly<ForageWikiProps>) {

	return (
		<div className={"boxed"}>
			<h2>Forage Wiki</h2>
			{props.forageWikiItems.length > 0 ?
				<>
					{props.forageWikiItems.map((forageWikiItem:ForageWikiItem) => (
							<div key={forageWikiItem.id}>
								<ExpandableCard
									forageWikiItem={forageWikiItem}
									fetchWikiData={props.fetchWikiData}/>
							</div>
						))}
				</>
				:
				<p>no Forage wiki items found</p>
			}

			<h2>Add ForageWikiItem</h2>
			<ForageWikiItemEditor fetchWikiData={props.fetchWikiData}/>

		</div>
	)
}