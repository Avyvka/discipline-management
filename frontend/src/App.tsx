import { Refine } from "@refinedev/core";
import { ColorModeContextProvider } from "./contexts/color-mode";
import routerProvider from "@refinedev/react-router";
import { BrowserRouter, Outlet, Route, Routes } from "react-router";
import springDataProvider from "./shared/data/provider/spring";

import "@refinedev/antd/dist/reset.css";
import { ThemedLayoutV2 } from "@refinedev/antd";
import { CourseCreate, CourseEdit, CourseList, CourseShow } from "./pages/courses";

function App() {
    return (
        <BrowserRouter>
            <ColorModeContextProvider>
                <Refine
                    dataProvider={springDataProvider("http://localhost:8080/api/v1")}
                    routerProvider={routerProvider}
                    resources={[
                        {
                            name: "courses",
                            list: "/ui/courses",
                            create: "/ui/courses/create",
                            edit: "/ui/courses/edit/:id",
                            show: "/ui/courses/show/:id",
                            meta: {
                                canDelete: true,
                            },
                        },
                    ]}
                >
                    <Routes>
                        <Route
                            element={
                                <ThemedLayoutV2>
                                    <Outlet />
                                </ThemedLayoutV2>
                            }
                        >
                            <Route path="/ui/courses">
                                <Route index element={<CourseList />} />
                                <Route path="show/:id" element={<CourseShow />} />
                                <Route path="create" element={<CourseCreate />} />
                                <Route path="edit/:id" element={<CourseEdit />} />
                            </Route>
                        </Route>
                    </Routes>
                </Refine>
            </ColorModeContextProvider>
        </BrowserRouter>
    );
}

export default App;
