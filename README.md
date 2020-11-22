פירוט המטלה בצורה כללית:
את קודקודי הגרף מימשתי בעזרת HashMap וואת שכני הקודקודים ייצגתי בעזרת HashMap<Integer, Edge>
כאשר המחלקה Edge הינה צלע ומכילה את ה ID של שני הקודקוד בקצוות הצלע ואת משקל הצלע.
בחרתי ב HashMap מכיוון שניתן באמצעות מבנה נתונים זה למצוא איבר ב O(1).

במחלקה הפנימית NodeInfo הוספתי תכונה int pred שמטרת היא לשמור את כתובת ה ID של הקודקוד האב לקודקוד הנוכחי (לפי אלגוריתם Dijkstra).
בנוסף, בהמשך להסבר למעלה את שכני הקודקודים ממישתי באמצעות HashMap<Integer, Edge> כאשר המפתחות הן כתובות ה ID של הקודקוד השכן 
והערכים הם הצלעות (אובייקטים מטיפוס Edge) בין השכנים.

זמני ריצה:
המחלקה WGraph_Algo:
הפונקציות הבאות הן בזמן ריצה O(1):
WGraph_Algo(), WGraph_Algo(weighted_graph g), init(weighted_graph g), getGraph(), 

copy() - (|V|*|V|)O
isConnected() - (|V|*|V|)O
shortestPathDist(int src, int dest) - (|V|*|V|)O
shortestPath(int src, int dest) - (|V|*|V|)O
Dijkstra(Object[] nodeArr, node_info s) - O(|V|*|V|*log|V|)
רוב הפונקציות במחלקה הן בסיבוכיות זמן ריצה (|V|*|V|)O אך הדבר נובע בעקבות זימון הפונקציה copy()
שמופעלת רק כאשר הגרף מתעדכן.


WGraph_DS:
WGraph_DS() - (1)O
WGraph_DS(weighted_graph other) - O(|V|*|E|)
hasEdge(int node1, int node2) - (1)O
getEdge(int node1, int node2) - (1)O
addNode(int key) - (1)O
connect(int node1, int node2, double w) - (1)O
getV() - (1)O
getV(int node_id) - (הרמה של הקודקוד)O
removeNode(int key) - (הרמה של הקודקוד)O
removeNode(int key) - (1)O
removeEdge(int node1, int node2) - (1)O
equals(Object other) - O(|V|*|V|)


NodeInfo:
NodeInfo(node_info other) - O(|E|)
NodeInfo(int key) - (1)O
equals(Object other) - (הרמה של הקודקוד)O
addNei(node_info n, double w) - (1)O
removeNei(node_info n) - (1)O
hasNei(node_info key) - (1)O
compareTo(node_info arg0) - (1)O