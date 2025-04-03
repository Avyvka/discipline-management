import { Refine } from "@refinedev/core";
import { ColorModeContextProvider } from "./contexts/color-mode";
import routerProvider from "@refinedev/react-router";
import { BrowserRouter, Outlet, Route, Routes } from "react-router";
import springDataProvider from "./shared/data/provider/spring";
import "@refinedev/antd/dist/reset.css";
import { ThemedLayoutV2 } from "@refinedev/antd";
import { CourseCreate, CourseEdit, CourseList, CourseShow } from "./pages/courses";
import resources from "./resources";
import { DisciplineCreate, DisciplineEdit, DisciplineList, DisciplineShow } from "./pages/disciplines";
import { LecturerCreate, LecturerEdit, LecturerList, LecturerShow } from "./pages/lecturers";
import { StudentCreate, StudentEdit, StudentList, StudentShow } from "./pages/students";

function App() {
    return (
        <BrowserRouter basename="/ui">
            <ColorModeContextProvider>
                <Refine
                    dataProvider={springDataProvider("http://localhost:8080/api/v1")}
                    routerProvider={routerProvider}
                    resources={resources}
                >
                    <Routes>
                        <Route
                            path="/"
                            element={
                                <ThemedLayoutV2>
                                    <Outlet />
                                </ThemedLayoutV2>
                            }
                        >
                            <Route path="courses">
                                <Route index element={<CourseList />} />
                                <Route path="show/:id" element={<CourseShow />} />
                                <Route path="create" element={<CourseCreate />} />
                                <Route path="edit/:id" element={<CourseEdit />} />
                            </Route>
                            <Route path="disciplines">
                                <Route index element={<DisciplineList />} />
                                <Route path="show/:id" element={<DisciplineShow />} />
                                <Route path="create" element={<DisciplineCreate />} />
                                <Route path="edit/:id" element={<DisciplineEdit />} />
                            </Route>
                            <Route path="lecturers">
                                <Route index element={<LecturerList />} />
                                <Route path="show/:id" element={<LecturerShow />} />
                                <Route path="create" element={<LecturerCreate />} />
                                <Route path="edit/:id" element={<LecturerEdit />} />
                            </Route>
                            <Route path="students">
                                <Route index element={<StudentList />} />
                                <Route path="show/:id" element={<StudentShow />} />
                                <Route path="create" element={<StudentCreate />} />
                                <Route path="edit/:id" element={<StudentEdit />} />
                            </Route>
                        </Route>
                    </Routes>
                </Refine>
            </ColorModeContextProvider>
        </BrowserRouter>
    );
}

export default App;
