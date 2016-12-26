package com.eflake.keyanimengine.bean;

import java.util.List;

/**
 * Created by xueyuanzhang on 16/12/22.
 */

public class AnimEntity {

    /**
     * anim : {"elements":[{"name":"red_packet","position":[{"time":0,"value":"540.0,-300.0"},{"time":12,"value":"540.0,277.5"}],"rotation":[{"time":12,"value":"60.0"},{"time":20,"value":"18.0"},{"time":26,"value":"-11.0"},{"time":30,"value":"10.0"},{"time":33,"value":"-5.0"},{"time":36,"value":"4.0"},{"time":38,"value":"0.0"}],"path":[{"time":0,"value":"600.0,-150.0","control":"412.0,226.0"},{"time":20,"value":"600.0,400.0","control":"412.0,226.0"},{"time":46,"value":"944.0,2096.0","control":"1388.0,1128.0"}],"scale":[{"time":112,"value":"1.0,1.0"},{"time":152,"value":"0.5,0.5"}],"alpha":[{"time":112,"value":"20.0"},{"time":152,"value":"100.0"}]},{"name":"m1","path":[{"time":0,"value":"600.0,-150.0","control":"412.0,226.0"},{"time":20,"value":"600.0,400.0","control":"412.0,226.0"},{"time":46,"value":"944.0,2096.0","control":"1388.0,1128.0"}],"rotation":[{"time":20,"value":"-21.0"},{"time":30,"value":"38.0"},{"time":46,"value":"-45.0"}]},{"name":"m2","path":[{"time":0,"value":"556.0,-64.0"},{"time":20,"value":"404.0,400.0","control":"470.0,184.0"},{"time":46,"value":"-152.0,804.0","control":"314.0,726.0"}],"rotation":[{"time":20,"value":"-4.0"},{"time":46,"value":"204.0"}]},{"name":"m4","path":[{"time":0,"value":"532.0,-192.0"},{"time":20,"value":"480.0,468.0","control":"392.0,210.0"},{"time":46,"value":"308.0,2100.0","control":"708.0,1112.0"}],"rotation":[{"time":20,"value":"0.0"},{"time":46,"value":"125.0"}]},{"name":"g1","path":[{"time":0,"value":"652.0,-155.0"},{"time":16,"value":"360.0,265.0","control":"552.0,-20.0"},{"time":46,"value":"360.0,2007.0","control":"-172.0,1120.0,660.0,1596.0"}]},{"name":"g2","path":[{"time":0,"value":"400.0,-132.0"},{"time":16,"value":"672.0,324.0","control":"856.0,24.0"},{"time":46,"value":"672.0,2007.0","control":"264.0,1040.0,884.0,1464.0"}],"scale":[{"time":112,"value":"1.0,1.0"},{"time":152,"value":"0.5,0.5"}],"alpha":[{"time":112,"value":"20.0"},{"time":152,"value":"100.0"}]}],"name":"red_packet","duration":180}
     */

    private AnimBean anim;

    public AnimBean getAnim() {
        return anim;
    }

    public void setAnim(AnimBean anim) {
        this.anim = anim;
    }

    public static class AnimBean {
        /**
         * elements : [{"name":"red_packet","position":[{"time":0,"value":"540.0,-300.0"},{"time":12,"value":"540.0,277.5"}],"rotation":[{"time":12,"value":"60.0"},{"time":20,"value":"18.0"},{"time":26,"value":"-11.0"},{"time":30,"value":"10.0"},{"time":33,"value":"-5.0"},{"time":36,"value":"4.0"},{"time":38,"value":"0.0"}]},{"name":"m1","path":[{"time":0,"value":"600.0,-150.0"},{"time":20,"value":"600.0,400.0","control":"412.0,226.0"},{"time":46,"value":"944.0,2096.0","control":"1388.0,1128.0"}],"rotation":[{"time":20,"value":"-21.0"},{"time":30,"value":"38.0"},{"time":46,"value":"-45.0"}]},{"name":"m2","path":[{"time":0,"value":"556.0,-64.0"},{"time":20,"value":"404.0,400.0","control":"470.0,184.0"},{"time":46,"value":"-152.0,804.0","control":"314.0,726.0"}],"rotation":[{"time":20,"value":"-4.0"},{"time":46,"value":"204.0"}]},{"name":"m4","path":[{"time":0,"value":"532.0,-192.0"},{"time":20,"value":"480.0,468.0","control":"392.0,210.0"},{"time":46,"value":"308.0,2100.0","control":"708.0,1112.0"}],"rotation":[{"time":20,"value":"0.0"},{"time":46,"value":"125.0"}]},{"name":"g1","path":[{"time":0,"value":"652.0,-155.0"},{"time":16,"value":"360.0,265.0","control":"552.0,-20.0"},{"time":46,"value":"360.0,2007.0","control":"-172.0,1120.0,660.0,1596.0"}]},{"name":"g2","path":[{"time":0,"value":"400.0,-132.0"},{"time":16,"value":"672.0,324.0","control":"856.0,24.0"},{"time":46,"value":"672.0,2007.0","control":"264.0,1040.0,884.0,1464.0"}],"scale":[{"time":112,"value":"1.0,1.0"},{"time":152,"value":"0.5,0.5"}],"alpha":[{"time":112,"value":"20.0"},{"time":152,"value":"100.0"}]}]
         * name : red_packet
         * duration : 180
         */

        private String name;
        private int duration;
        private List<ElementsBean> elements;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public List<ElementsBean> getElements() {
            return elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }

        public static class ElementsBean {
            /**
             * name : red_packet
             * position : [{"time":0,"value":"540.0,-300.0"},{"time":12,"value":"540.0,277.5"}]
             * rotation : [{"time":12,"value":"60.0"},{"time":20,"value":"18.0"},{"time":26,"value":"-11.0"},{"time":30,"value":"10.0"},{"time":33,"value":"-5.0"},{"time":36,"value":"4.0"},{"time":38,"value":"0.0"}]
             * path : [{"time":0,"value":"600.0,-150.0"},{"time":20,"value":"600.0,400.0","control":"412.0,226.0"},{"time":46,"value":"944.0,2096.0","control":"1388.0,1128.0"}]
             * scale : [{"time":112,"value":"1.0,1.0"},{"time":152,"value":"0.5,0.5"}]
             * alpha : [{"time":112,"value":"20.0"},{"time":152,"value":"100.0"}]
             */

            private String name;
            private List<PositionBean> position;
            private List<RotationBean> rotation;
            private List<PathBean> path;
            private List<ScaleBean> scale;
            private List<AlphaBean> alpha;
            private String parent;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParent() {
                return parent;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public List<PositionBean> getPosition() {
                return position;
            }

            public void setPosition(List<PositionBean> position) {
                this.position = position;
            }

            public List<RotationBean> getRotation() {
                return rotation;
            }

            public void setRotation(List<RotationBean> rotation) {
                this.rotation = rotation;
            }

            public List<PathBean> getPath() {
                return path;
            }

            public void setPath(List<PathBean> path) {
                this.path = path;
            }

            public List<ScaleBean> getScale() {
                return scale;
            }

            public void setScale(List<ScaleBean> scale) {
                this.scale = scale;
            }

            public List<AlphaBean> getAlpha() {
                return alpha;
            }

            public void setAlpha(List<AlphaBean> alpha) {
                this.alpha = alpha;
            }

            public static class PositionBean {
                /**
                 * time : 0
                 * value : 540.0,-300.0
                 */

                private int time;
                private String value;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class RotationBean {
                /**
                 * time : 12
                 * value : 60.0
                 */

                private int time;
                private String value;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class PathBean {
                /**
                 * time : 0
                 * value : 600.0,-150.0
                 * control : 412.0,226.0
                 */

                private int time;
                private String value;
                private String control;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getControl() {
                    return control;
                }

                public void setControl(String control) {
                    this.control = control;
                }
            }

            public static class ScaleBean {
                /**
                 * time : 112
                 * value : 1.0,1.0
                 */

                private int time;
                private String value;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class AlphaBean {
                /**
                 * time : 112
                 * value : 20.0
                 */

                private int time;
                private String value;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
